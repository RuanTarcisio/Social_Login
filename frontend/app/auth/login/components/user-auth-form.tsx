"use client";

import * as React from "react";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { toast } from "sonner";
import { useAuthGuard } from "@/lib/auth/use-auth";
import { HttpErrorResponse } from "@/models/http/HttpErrorResponse";
import ErrorFeedback from "@/components/error-feedback";
import { FaGithub, FaGoogle } from "react-icons/fa";

interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> {}

const loginFormSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(8, "Password must be at least 8 characters"),
});

type Schema = z.infer<typeof loginFormSchema>;

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const [isLoading, setIsLoading] = React.useState<boolean>(false);
  const { login } = useAuthGuard({
    middleware: "guest",
    redirectIfAuthenticated: "/profile",
  });
  const [errors, setErrors] = React.useState<HttpErrorResponse | undefined>(undefined);

  const { register, handleSubmit, formState } = useForm<Schema>({
    resolver: zodResolver(loginFormSchema),
    reValidateMode: "onSubmit",
  });

  async function onSubmit(data: Schema) {
    setIsLoading(true);
  
    login({
      onError: (errors) => {
        setErrors(errors);
        toast.error("Authentication failed");
        setIsLoading(false); // Reset o estado no caso de erro
      },
      props: data,
    })
      .then(() => {
        // Sucesso no login
        setIsLoading(false);
      })
      .catch(() => {
        // Falha inesperada
        setIsLoading(false);
      });
  }

  function getProviderLoginUrl(provider: "google" | "github") {
    const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
    if (!baseUrl) {
      console.error("NEXT_PUBLIC_BASE_URL is not defined");
      console.log(process.env.NEXT_PUBLIC_BASE_URL)
      return "#"; // Fallback em caso de erro
    }
    return `${baseUrl}/oauth2/authorization/${provider}`;
  }

  return (
    <div className="grid gap-6">
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="grid gap-2">
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            placeholder="name@example.com"
            aria-invalid={formState.errors.email ? "true" : "false"}
            aria-describedby={formState.errors.email ? "email-error" : undefined}
            {...register("email")}
            disabled={isLoading}
          />
          {formState.errors.email && (
            <small id="email-error" className="text-red-600">
              {formState.errors.email.message}
            </small>
          )}

          <Label htmlFor="password">Password</Label>
          <Input
            id="password"
            type="password"
            aria-invalid={formState.errors.password ? "true" : "false"}
            aria-describedby={formState.errors.password ? "password-error" : undefined}
            {...register("password")}
            disabled={isLoading}
          />
          {formState.errors.password && (
            <small id="password-error" className="text-red-600">
              {formState.errors.password.message}
            </small>
          )}

          <ErrorFeedback data={errors} />

          <Button type="submit" disabled={isLoading}>
            {isLoading ? "Logging in..." : "Sign In with Email"}
          </Button>
        </div>
      </form>

      <div className="relative">
        <div className="absolute inset-0 flex items-center">
          <span className="w-full border-t" />
        </div>
        <div className="relative flex justify-center text-xs uppercase">
          <span className="bg-background px-2 text-muted-foreground">
            Or continue with
          </span>
        </div>
      </div>

      <div className="flex flex-col gap-y-2">
        <Button
          variant="outline"
          type="button"
          disabled={isLoading}
          className="w-full"
          onClick={() => (window.location.href = getProviderLoginUrl("github"))}
        >
          <FaGithub className="mr-2 h-4 w-4" />
          GitHub
        </Button>

        <Button
          variant="outline"
          type="button"
          disabled={isLoading}
          className="w-full"
          onClick={() => (window.location.href = getProviderLoginUrl("google"))}
        >
          <FaGoogle className="mr-2 h-4 w-4" />
          Google
        </Button>
      </div>
    </div>
  );
}
