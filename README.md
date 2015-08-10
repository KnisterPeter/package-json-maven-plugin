# package-json-maven-plugin

This mojo reads a package.json file and stores the name and version fields as properties in the maven build process.
This could be useful e.g. to name build artifacts with package.json name and version.

# Configuration

    <build>
      <plugins>
        <plugin>
          <groupId>de.matrixweb</groupId>
          <artifactId>package-json-maven-plugin</artifactId>
          <version>0.0.1-SNAPSHOT</version>
          <executions>
            <execution>
              <goals>
                <goal>package-json</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </build>

After executing this goal the properties `package.json.name` and `package.json.version` are defined.

# Usage

For example name the main build artifacts of a maven build with this properties:

    <build>
      <finalName>${package.json.name}-${package.json.version}</finalName>
    </build>
