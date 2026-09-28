def call(String taskDefinitionFile, String taskFamily, String ecsService) {

    if (params.Environment == 'dev') {

        sh """
            sed -i 's/\\\${ENV}/${params.Environment}/g' ${taskDefinitionFile}
            sed -i 's/\\\${COMMIT_ID}/${env.GIT_COMMIT}/g' ${taskDefinitionFile}
        """

    } else {

        sh """
            sed -i 's/\\\${ENV}/${params.Environment}/g' ${taskDefinitionFile}
            sed -i 's/\\\${COMMIT_ID}/${params.IMAGE_TAG}/g' ${taskDefinitionFile}
        """
    }

    def taskRevision = sh(
        script: """
            aws ecs register-task-definition \
                --cli-input-json file://${taskDefinitionFile} \
                --region ${env.AWS_REGION} \
                --query 'taskDefinition.revision' \
                --output text
        """,
        returnStdout: true
    ).trim()

    sh """
        aws ecs update-service \
            --cluster ${env.ECS_CLUSTER} \
            --service ${ecsService} \
            --task-definition ${taskFamily}:${taskRevision} \
            --region ${env.AWS_REGION}
    """
}
