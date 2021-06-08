# integracao-clicksign-1.9
Projeto criado para integrar com o gerenciador de assinaturas de documentos on-line ClickSign.

## Instruções
Renomear o Arquivo <b>application.properties.default</b> para <b>application.properties</b> e incluir o token da click-sign.<br/>
https://api-lojavirtual-capitani.herokuapp.com/swagger-ui.html
<br/><br/>


Startar o Projeto Local (com maven instalado)<br/>
```
./mvnw spring-boot:run
```
<br/>

REQUEST - Verificar Token da ClickSign<br/>
```
http://localhost:8080/account?access_token=seu_token
```
RESPONSE<br/>
```
{
  "account": {
    "name": "NomeDominio",
    "key": "234234-0000-400-b007-01e8000000bd",
    "admins": [
      "emailregistrado@gmail.com"
    ]
  }
}
```
<br/><br/>
REQUEST - Criando um Usuário para assinar um Documento<br/>
```
{
  "signer": {
    "email": "seuemail@gmail.com",
    "phone_number": "11000000000",
    "auths": ["whatsapp"],
    "name": "Fulano da Silva",
    "documentation": "960.863.710-40",
    "birthday": "2001-11-21",
    "has_documentation": true,
    "selfie_enabled": false,
    "handwritten_enabled": false,
    "official_document_enabled": false
  }
}
--OBS: CPF deve ser válido!
--OBS: O auths deve ser pra cada canal (sms ou whatsapp ou email)
```
RESPONSE<br/>
```
{
  "signer": {
    "key": "74f1662e-cf7c-4eb6-9576-d28f823fc2d0",
    "email": "emailregistrado@gmail.com",
    "auths": [
      "whatsapp"
    ],
    "delivery": "whatsapp",
    "name": "Fulano da Silva",
    "documentation": "960.863.710-40",
    "selfie_enabled": false,
    "handwritten_enabled": false,
    "birthday": "2001-11-21",
    "phone_number": "11000000000",
    "has_documentation": true,
    "created_at": "2021-06-04T14:16:37.261-03:00",
    "updated_at": "2021-06-04T14:16:37.261-03:00",
    "official_document_enabled": false
  }
}
```
<br/><br/>
REQUEST - Criando Documento que irá receber Assinatura<br/>
```
{
  "document": {
    "path": "/Contrato.pdf",
    "content_base64": "data:application/pdf;base64,JVBERi0xLjcNCiXi48/TDQo0IDAgb2JqDQo8PA0KL...",
    "deadline_at": "2021-07-10T14:30:59-03:00",
    "auto_close": true,
    "locale": "pt-BR",
    "sequence_enabled": false
  }
}
```
RESPONSE<br/>
```
{
  "document": {
    "key": "9f01db9a-1f10-4950-962b-29504ced0a42",
    "account_key": "15a0f074-f7e6-4c74-bc77-01e830e221bd",
    "path": "/Contrato.pdf",
    "filename": "Contrato.pdf",
    "uploaded_at": "2021-06-04T17:25:30.091Z",
    "updated_at": "2021-06-04T17:25:30.376Z",
    "finished_at": null,
    "deadline_at": "2021-06-10T14:30:59.000-03:00",
    "status": "running",
    "auto_close": true,
    "locale": "pt-BR",
    "metadata": {},
    "sequence_enabled": false,
    "signable_group": null,
    "remind_interval": null,
    "downloads": {
      "original_file_url": "https://clicksign-sandbox-content.s3.amazonaws.com/2021/06/..."
    },
    "template": null,
    "signers": [],
    "events": [
      {
        "name": "upload",
        "data": {
          "user": {
            "email": "emailresgistrado@gmail.com",
            "name": "Fulano dos Santos"
          },
          "account": {
            "key": "15a0f074-f7e6-4c74-bc77-01e830e221bd"
          },
          "deadline_at": "2021-06-10T14:30:59.000-03:00",
          "auto_close": true,
          "locale": "pt-BR"
        },
        "occurred_at": "2021-06-04T17:25:30.383Z"
      }
    ],
    "attachments": []
  }
}
```
<br/><br/>
REQUEST - Adicionando Usuários ao Documento para realização do vínculo<br/>
```
{
  "list": {
    "document_key": "9f01db9a-1f10-4950-962b-29504ced0a42",
    "signer_key": "74f1662e-cf7c-4eb6-9576-d28f823fc2d0",
    "sign_as": "sign",
    "message": "Prezado Usuário,\nPor favor assine o documento.\n\nQualquer dúvida estou à disposição.\n\nAtenciosamente,\nFulano dos Santos"
  }
}
```
RESPONSE<br/>
```
{
  "list": {
    "key": "abf36a12-bfc4-4b12-a300-1ee94f547ea4",
    "request_signature_key": "4b3e1cd8-52f5-447e-b566-b146b89831ee",
    "document_key": "9f01db9a-1f10-4950-962b-29504ced0a42",
    "signer_key": "74f1662e-cf7c-4eb6-9576-d28f823fc2d0",
    "sign_as": "sign",
    "created_at": "2021-06-04T14:47:01.632-03:00",
    "updated_at": "2021-06-04T14:47:01.632-03:00",
    "url": "https://sandbox.clicksign.com/sign/4b3e1cd8-52f5-447e-b566-b146b89831ee",
    "message": "Prezado Usuário,\nPor favor assine o documento.\n\nQualquer dúvida estou à disposição.\n\nAtenciosamente,\nFulano dos Santos"
  }
}
```
<br/><br/>
REQUEST - Enviando notificação por SMS ou WhatsApp<br/>
```
{
  "request_signature_key": "4b3e1cd8-52f5-447e-b566-b146b89831ee"
}
```
RESPONSE<br/>
```
{
  "message": "Notificação via SMS/WhatsApp enviada com sucesso!"
}
```
<br/><br/>
REQUEST - Enviando notificação por E-mail<br/>
```
{
  "request_signature_key": "4b3e1cd8-52f5-447e-b566-b146b89831ee",
  "message": "Prezado Usuário (E-mail),\nPor favor assine o documento.\n\nQualquer dúvida estou à disposição.\n\nAtenciosamente,\Fulano dos Santos",
  "url": "https://sandbox.clicksign.com/sign/4b3e1cd8-52f5-447e-b566-b146b898111"
}
```
RESPONSE<br/>
```
{
  "message": "Notificação via E-mail enviada com sucesso!"
}
```