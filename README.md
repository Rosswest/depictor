# Depictor Service
A Spring Boot service for simple chemical depiction using CDK. The main purpose of this service is to take a string representation of a molecule ([SMILES](https://wikipedia.org/wiki/Simplified_molecular-input_line-entry_system)) and create a graphic representation. The service supports PNG, JPEG, GIF and SVG rendering.

# How to use
This tool can be used in two ways, either as a standalone service which can be tested via its swagger page or as a companion to the depictor-ui.

# Setup
1. Checkout code
2. Deploy the service locally by either:
   * Run the maven 'clean package' goal and deploy the generated .jar using 'java -jar -server JAR_NAME'
   * OR
   * Run 'dist/run_with_gui.bat'
3. You can access the service's depiction endpoints via its [swagger page](http://localhost:8080/swagger-ui.html)
4. A dedicated UI is available with the [depictor-ui](https://github.com/Rosswest/depictor-ui) which must also be deployed locally. **Note that the depiction service MUST be deployed to port 8080.**
