
INSERT INTO auth_user (username,email, password, activated) VALUES ('phitran', 'test1@mail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', true);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username,authority) VALUES ('phitran', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('phitran', 'ROLE_ADMIN');

INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, additional_information)
VALUES ('product-service', 'secret', 'read', 'authorization_code,password,refresh_token,implicit', '900', '{}');
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, additional_information)
VALUES ('cart-service', 'secret', 'read', 'authorization_code,password,refresh_token,implicit', '900', '{}');