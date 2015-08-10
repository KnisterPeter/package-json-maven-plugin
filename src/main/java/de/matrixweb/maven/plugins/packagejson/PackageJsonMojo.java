package de.matrixweb.maven.plugins.packagejson;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.jackson.map.ObjectMapper;

@Mojo(name = "package-json", defaultPhase = LifecyclePhase.VALIDATE)
public final class PackageJsonMojo extends AbstractMojo {

  @Parameter(property = "skip.npm", defaultValue = "false")
  private Boolean skip;

  @Parameter(property = "workingDirectory", defaultValue = "${basedir}")
  private String workingDirectory;

  @Parameter(defaultValue = "${project}", readonly = true)
  MavenProject project;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!skip) {
      File packageJson = new File(workingDirectory, "package.json");
      try {
        Properties projectProps = project.getProperties();
        @SuppressWarnings("unchecked")
        Map<String, Object> props = new ObjectMapper().readValue(packageJson,
            Map.class);
        copyProperty("name", props, projectProps);
        copyProperty("version", props, projectProps);
      } catch (IOException e) {
        throw new MojoExecutionException("Failed to parse package.json", e);
      }
    }
  }

  private void copyProperty(String name, Map<String, Object> props,
      Properties projectProps) {
    if (props.containsKey(name)) {
      projectProps.setProperty("package.json." + name, props.get(name)
          .toString());
    }
  }

}
