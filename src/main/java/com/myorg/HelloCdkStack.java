package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
// Import Lambda function


public class HelloCdkStack extends Stack {
    public HelloCdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public HelloCdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // Define the Lambda function resource
        Function myFunction = Function.Builder.create(this, "HelloWorldFunction")
                .runtime(Runtime.NODEJS_20_X) // Provide any supported Node.js runtime
                .handler("index.handler")
                .code(Code.fromInline(
                        "exports.handler = async function(event) {" +
                                " return {" +
                                " statusCode: 200," +
                                " body: JSON.stringify('Hello CDK!')" +
                                " };" +
                                "};"))
                .role(Role.fromRoleArn(this, "LabRole", "arn:aws:iam::086756238045:role/LabRole"))
                .build();

        FunctionUrl myFunctionUrl = myFunction.addFunctionUrl(FunctionUrlOptions.builder()
                .authType(FunctionUrlAuthType.NONE)
                .build());

        // Define a CloudFormation output for your URL
        CfnOutput.Builder.create(this, "myFunctionUrlOutput")
                .value(myFunctionUrl.getUrl())
                .build();
    }
}