# Quarkus Example with Java and OIDC Authentication

A Quarkus example app that shows how to develop REST endpoints and secure them with MicroProfile JWT and OIDC.

Please read [How to Develop a Quarkus App with Java and OIDC Authentication](http://developer.okta.com/blog/2019/09/30/java-quarkus-oidc) to see how this app was created.

**Prerequisites:** [Java 11](https://adoptopenjdk.net/).

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage, and secure users + roles in any application.

* [Getting Started](#getting-started)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Getting Started

To install this example application, run the following commands:

```bash
git clone https://github.com/oktadeveloper/okta-quarkus-example.git
cd okta-quarkus-example
```

This will get a copy of the project installed locally. To run the example, run the following command:
 
```bash
./mvnw compile quarkus:dev
```

### Create an OIDC App in Okta

You will need to create an OIDC App in Okta to get a `clientId` to generate a JWT for authentication. 

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don’t have an account) and navigate to **Applications** > **Add Application**. Click **Web**, click **Next**, and give the app a name you’ll remember. Click **Done** and copy the `clientId`.

Put your Okta domain name into `src/main/resources/application.properties`. 

```properties
mp.jwt.verify.publickey.location=https://{yourOktaDomain}/oauth2/default/v1/keys
mp.jwt.verify.issuer=https://{yourOktaDomain}/oauth2/default
```

**NOTE:** The value of `{yourOktaDomain}` should be something like `dev-123456.okta.com`. Make sure you don't include `-admin` in the value!

After modifying this file, restart your app and it'll be protected by Okta! See this repo's [blog post](http://developer.okta.com/blog/2019/09/30/java-quarkus-oidc) to learn how to gain access to it with an OAuth 2.0 access token.

## Links

This example uses the [MicroProfile JWT RBAC security specification (MP-JWT)](https://www.eclipse.org/community/eclipse_newsletter/2017/september/article2.php) to integrate with Okta.

## Help

Please post any questions as comments on the [blog post](http://developer.okta.com/blog/2019/09/30/java-quarkus-oidc), or visit our [Okta Developer Forums](https://devforum.okta.com/). 

## License

Apache 2.0, see [LICENSE](LICENSE).
