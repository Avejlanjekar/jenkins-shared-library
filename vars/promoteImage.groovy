def call(String sourceImage, String targetImage) {

    docker.withRegistry(
        "${env.ECR_REGISTRY_URL}",
        "${env.ECR}:${env.AWS_REGION}:${env.AWS_CREDENTIALS}"
    ) {

        docker.image(
            "${env.ECR_REGISTRY}/${sourceImage}:${params.IMAGE_TAG}"
        ).pull()
    }

    sh """
        docker tag \
        ${env.ECR_REGISTRY}/${sourceImage}:${params.IMAGE_TAG} \
        ${env.ECR_REGISTRY}/${targetImage}:${params.IMAGE_TAG}
    """

    docker.withRegistry(
        "${env.ECR_REGISTRY_URL}",
        "${env.ECR}:${env.AWS_REGION}:${env.AWS_CREDENTIALS}"
    ) {

        docker.image(
            "${env.ECR_REGISTRY}/${targetImage}:${params.IMAGE_TAG}"
        ).push()
    }

    echo "Image promoted successfully"
    echo "${env.ECR_REGISTRY}/${targetImage}:${params.IMAGE_TAG}"
}
