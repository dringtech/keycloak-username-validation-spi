/**
 * MIT License
 *
 * Copyright (c) 2021 Giles Dring, Dring Technology Solutions
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package tech.dring.keycloak;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class UsernameValidatorFactory implements FormActionFactory {

  public static final String PROVIDER_ID = "username-validation";
  private static final String PROVIDER_NAME = "Username Validation";
  private static final String PROVIDER_HELP_TEXT = "Validates usernames against a regex.";

  protected static final String USERNAME_VALIDATION_REGEX = "username.regex";
  protected static final String DEFAULT_VALIDATION = "^[\\w\\-\\.]+$";
  private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<ProviderConfigProperty>();

  @Override
  public FormAction create(KeycloakSession session) {
    return new UsernameValidator();
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getDisplayType() {
    return PROVIDER_NAME;
  }

  @Override
  public String getHelpText() {
    return PROVIDER_HELP_TEXT;
  }

  @Override
  public void close() {
    // complete
  }

  @Override
  public void init(Scope scope) {
    // complete
  }

  @Override
  public void postInit(KeycloakSessionFactory sessionFactory) {
    // complete
  }

  @Override
  public String getReferenceCategory() {
    return null;
  }

  private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
      AuthenticationExecutionModel.Requirement.REQUIRED, AuthenticationExecutionModel.Requirement.DISABLED };

  @Override
  public Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public boolean isConfigurable() {
    return true;
  }

  @Override
  public boolean isUserSetupAllowed() {
    return false;
  }

  // Configuration
  static {
    ProviderConfigProperty property;
    property = new ProviderConfigProperty();
    property.setName(USERNAME_VALIDATION_REGEX);
    property.setDefaultValue(DEFAULT_VALIDATION);
    property.setLabel("Username validation Regex");
    property.setType(ProviderConfigProperty.STRING_TYPE);
    property.setHelpText("Regular expression");
    CONFIG_PROPERTIES.add(property);
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return CONFIG_PROPERTIES;
  }
}