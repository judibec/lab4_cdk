# Welcome to your CDK Java project!

This is a blank project for CDK development with Java.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!

## Arquitectura
Se crea un cloud formation, en el cual se tiene una lambda basica respondiendo un Hello World, se configura para que entregue una url y asi poder consultarla desde un browser

## Explicación
Como Prerequisitos debemos tener instalado Maven, aws-cli y aws-cdk, se crea una lambda en un codigo java local, en el cual configuramos la cuenta de aws asociada y la region

con los comandos `aws sts get-caller-identity --query "Account" --output text` y `aws configure get region` 

Para inicializar el comando `cdk bootstrap` debemos primero editar los roles, ya que por defecto este va a crear roles y no con tamos con ese permiso, con el comando `powershell "cdk bootstrap --show-template | Out-File -encoding utf8 bootstrap-template.yaml"` empezamos editando el template y debemos comentar de la linea 272 - 569, asi evitamos la creacion de roles y en la linea 159 debemos permitir todos los roles `Fn::Sub: "*"`, luego decimos que use ese template para inicializar `cdk bootstrap --template bootstrap-template.yaml`, en el codigo de la lambda especificamos que usuario debe usarse pues por defecto crea uno nuevo, se agrega la siguiente linea en el codigo de la lambda `.role(Role.fromRoleArn(this, "LabRole", "arn:aws:iam::086756238045:role/LabRole"))` con el ARN respectivo del rol, compilamos con maven y sintetizamos el proyecto, es decir lo preparamos para desplegar `cdk synth`, y por ultimo hacemos el deploy pero con el usuario al que tenemos acceso, con el siguiente comando `cdk deploy -r arn:aws:iam::086756238045:role/LabRole`. Cuando se termine de desplegar nos arrojara una url la cual mostrara lo que colocamos en la lambda, es decir el Hello World

## Evidencias
Ejecucion del comando de bootstrap
![image](https://github.com/user-attachments/assets/79b93f03-f178-4a4b-a765-401b0c48cfe0)


Se ve la creacion del cloud formation 
![image](https://github.com/user-attachments/assets/2a503842-8945-420a-a384-882ecd855af0)

Se realiza el despliegue
![image](https://github.com/user-attachments/assets/a005f94f-f463-4adf-983a-f6e4eaebbc49)
![image](https://github.com/user-attachments/assets/81f36c96-2fd1-4005-b36f-952c92000b5a)

Vemos el resultado en la url que nos muestra el despliegue
![image](https://github.com/user-attachments/assets/af2d0f98-af0f-4e01-9614-13b4e31e13ce)

Se realiza un cambio y vemos que cambios se hicieron 
![image](https://github.com/user-attachments/assets/7233350f-022f-4386-bc56-44083d03df64)

Luego realizamos el despliegue y consultamos nuevamente el link con el mensaje actualizado 
![image](https://github.com/user-attachments/assets/a7bc65cc-d6e1-4680-860d-b10ef2d9baca)
![image](https://github.com/user-attachments/assets/4bb8c74f-6f61-40f0-b1a8-7238e237d583)
