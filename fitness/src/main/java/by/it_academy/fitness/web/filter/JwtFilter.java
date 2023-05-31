package by.it_academy.fitness.web.filter;

import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.service.api.user.IUserService;
import by.it_academy.fitness.web.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final IUserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ConversionService conversionService;

    public JwtFilter(IUserService userService, JwtTokenUtil jwtTokenUtil, ConversionService conversionService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.conversionService = conversionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }
        UserDTO userDTO = conversionService.convert(userService.getUser(jwtTokenUtil.extractUsername(token)),UserDTO.class);
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDTO, null, userDTO.getAuthorities());


        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}