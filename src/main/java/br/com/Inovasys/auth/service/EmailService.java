package br.com.Inovasys.auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void enviarEmailBoasVindas(String para, String senha) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject("🚀 Bem-vindo ao InovaSys - Sua conta foi criada com sucesso!");

        mensagem.setText(
                "Olá!\n\n" +
                        "Seja muito bem-vindo(a) ao InovaSys — sua plataforma de gestão modular inteligente.\n\n" +
                        "Seu cadastro foi realizado com sucesso e sua conta já está ativa.\n\n" +
                        "🔐 Senha temporária: " + senha + "\n\n" +
                        "Por segurança, recomendamos que você altere sua senha no primeiro acesso.\n" +
                        "Para isso, acesse seu perfil dentro da plataforma e defina uma nova senha.\n\n" +
                        "Estamos felizes em ter você conosco!\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe InovaSys\n" +
                        "Gestão Inteligente para Negócios Modernos"
        );

        mailSender.send(mensagem);
    }
}
