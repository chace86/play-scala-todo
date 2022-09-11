window.onload = async function() {
  const cookies = new UniversalCookie();
  window.ui = SwaggerUIBundle({
    // edit url
    url: "/assets/swagger.json",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout",
    requestInterceptor: request => {
      request.headers['Csrf-Token'] = cookies.get('CSRF_TOKEN');
      return request;
    }
  });
};