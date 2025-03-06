//package gov.nist.hit.hl7.tcamt.auth.config;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//
//  private final TokenAuthenticationService tokenService;
//  private final RequestMatcher pathMatcher;
//
//  public JWTAuthenticationFilter(String path, TokenAuthenticationService tokenService) {
//    this.pathMatcher = new AntPathRequestMatcher(path);
//    this.tokenService = tokenService;
//  }
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//          throws ServletException, IOException {
//
//    if (!pathMatcher.matches(request)) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//
//    try {
//      UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(request);
//      SecurityContextHolder.getContext().setAuthentication(authentication);
//      filterChain.doFilter(request, response);
//    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
//             | SignatureException | IllegalArgumentException | NoSuchAlgorithmException
//             | InvalidKeySpecException e) {
//      handleAuthenticationException(response, e);
//    }
//  }
//
//  private void handleAuthenticationException(HttpServletResponse response, Exception e) throws IOException {
//    e.printStackTrace();
//    Cookie authCookie = new Cookie("tcamt-auth", "");
//    authCookie.setPath("/api");
//    authCookie.setMaxAge(0);
//    response.addCookie(authCookie);
//    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    response.getWriter().write("Authentication failed: " + e.getMessage());
//  }
//}
