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

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.logging.Logger;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;

public class UsernameValidator implements FormAction {

  private static final Logger logger = Logger.getLogger(UsernameValidator.class);

  @Override
  public void validate(ValidationContext context) {
    AuthenticatorConfigModel authConfig = context.getAuthenticatorConfig();
    String validationRegex;

    try {
      validationRegex = authConfig.getConfig().get(UsernameValidatorFactory.USERNAME_VALIDATION_REGEX);
    } catch (Exception e) {
      validationRegex = UsernameValidatorFactory.DEFAULT_VALIDATION;
    }

    MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    List<FormMessage> errors = new ArrayList<>();
    boolean success = false;

    // Validate username field and store result in success
    String username = formData.getFirst(Validation.FIELD_USERNAME);

    if (username != null) {
      logger.info("Validating Username: '" + username + "' against /" + validationRegex + "/");
      success = username.matches(validationRegex);
    }

    if (success) {
      context.success();
    } else {
      errors.add(new FormMessage(null, Messages.INVALID_USERNAME));
      formData.remove(Validation.FIELD_USERNAME);
      context.validationError(formData, errors);
      return;
    }
  }

  @Override
  public void close() {
    // complete
  }

  @Override
  public void buildPage(FormContext context, LoginFormsProvider loginFormsProvider) {
    // complete
  }

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realmModel, UserModel userModel) {
    return false;
  }

  @Override
  public boolean requiresUser() {
    return false;
  }

  @Override
  public void setRequiredActions(KeycloakSession session, RealmModel realmModel, UserModel userModel) {
    // complete
  }

  @Override
  public void success(FormContext context) {
    // complete
  }
}