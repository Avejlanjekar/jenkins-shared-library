def call(String imagename){
    echo "Building docker image ${imagename}"
    def app= docker.build("${imagename}:${GIT_COMMIT}")
}