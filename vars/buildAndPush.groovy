def call(String imageRepository, String buildContext) {

    echo "Repository : ${imageRepository}"
    echo "Commit     : ${env.GIT_COMMIT}"

    def image = docker.build(
        "${env.ECR_REGISTRY}/${imageRepository}:${env.GIT_COMMIT}",
        buildContext
    )

    docker.withRegistry(
        "${env.ECR_REGISTRY_URL}",
        "${env.ECR}:${env.AWS_REGION}:${env.AWS_CREDENTIALS}"
    ) {
        image.push()
    }

    echo "Image pushed successfully"
    echo "${env.ECR_REGISTRY}/${imageRepository}:${env.GIT_COMMIT}"
}
