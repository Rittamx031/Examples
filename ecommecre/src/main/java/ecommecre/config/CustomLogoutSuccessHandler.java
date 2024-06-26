package ecommecre.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
      final Authentication authentication) throws IOException, ServletException {
    final String targetUrl = determineTargetUrl(authentication);
    removeSessionAuthenAttributes(request);
    this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(Authentication authentication) {
    String url = "";
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    List<String> roles = new ArrayList<String>();
    for (GrantedAuthority a : authorities) {
      roles.add(a.getAuthority());
    }
    if (roles.contains("NOT_ACCTIVE")) {
      url = "/login";
    } else if (roles.contains("ADMIN")) {
      url = "/login";
    } else if (roles.contains("STAFF")) {
      url = "/login";
    } else if (roles.contains("USER")) {
      url = "/index";
    }
    return url;
  }

  public void removeSessionAuthenAttributes(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute("authenuser");
    session.removeAttribute("authenemployee");
  }
}
