<h1>📚 <strong>Estudo de Autenticação com OAuth e Envio de E-mails</strong></h1>
<p>Este projeto tem como objetivo implementar uma página de login no <strong>front-end</strong> e um <strong>back-end</strong> para autenticação de usuários usando <strong>OAuth2</strong> (Google e GitHub), além de enviar e-mails de redefinição de senha.</p>

<hr>

<h2>🚀 <strong>Tecnologias Utilizadas</strong></h2>
<ul>
    <li><strong>Front-end</strong>: <a href="https://nextjs.org/" target="_blank">Next.js</a>, <a href="https://tailwindcss.com/" target="_blank">Tailwind CSS</a>, <a href="https://www.typescriptlang.org/" target="_blank">TypeScript</a></li>
    <li><strong>Back-end</strong>: <a href="https://www.java.com/" target="_blank">Java</a>, <a href="https://spring.io/projects/spring-boot" target="_blank">Spring Boot</a></li>
    <li><strong>Autenticação</strong>: OAuth2 (Google, GitHub), JWT (JSON Web Token)</li>
    <li><strong>Envio de E-mails</strong>: JavaMail API, Thymeleaf</li>
    <li><strong>Banco de Dados</strong>: <a href="https://www.mysql.com/" target="_blank">MySQL</a></li>
</ul>

<hr>

<h2>🔑 <strong>Funcionalidades</strong></h2>
<h3>1. Login via OAuth2:</h3>
<p>O usuário pode fazer login utilizando contas do <strong>Google</strong> ou <strong>GitHub</strong>.</p>
<p>Após a autenticação, um <strong>token de acesso</strong> é gerado para comunicação segura.</p>

<h3>2. Login Local:</h3>
<p>O usuário pode fazer login usando <strong>e-mail</strong> e <strong>senha</strong>.</p>
<p>Utiliza autenticação baseada em <strong>JWT</strong>.</p>

<h3>3. Envio de E-mails:</h3>
<p>O sistema envia <strong>e-mails</strong> para:</p>
<ul>
    <li>Redefinição de senha com links para o usuário.</li>
    <li>Cadastro de novos usuários.</li>
    <li>Recuperação de senha.</li>
</ul>

<h3>4. Página de Login:</h3>
<p>Interface simples de login com opções para login via <strong>Google</strong> ou <strong>GitHub</strong>.</p>
