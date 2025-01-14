:book: Estudo de Autenticação com OAuth e Envio de E-mails
Este projeto tem como objetivo implementar uma página de login no front-end e um back-end para autenticação de usuários usando OAuth2 (Google e GitHub), além de enviar e-mails de redefinição de senha.

:rocket: Tecnologias Utilizadas
Front-end:

Next.js
Tailwind CSS
TypeScript
Back-end:

Java
Spring Boot
Autenticação:

OAuth2 (Google, GitHub)
JWT (JSON Web Token)
Envio de E-mails:

JavaMail API
Thymeleaf
Banco de Dados:

MySQL
:key: Funcionalidades
1. Login via OAuth2:
   O usuário pode fazer login utilizando contas do Google ou GitHub.
   Após a autenticação, um token de acesso é gerado para comunicação segura.
2. Login Local:
   O usuário pode fazer login usando e-mail e senha.
   Utiliza autenticação baseada em JWT.
3. Envio de E-mails:
   O sistema envia e-mails para:
   Redefinição de senha com links para o usuário.
   Cadastro de novos usuários.
   Recuperação de senha.
4. Página de Login:
   Interface simples de login com opções para login via Google ou GitHub.