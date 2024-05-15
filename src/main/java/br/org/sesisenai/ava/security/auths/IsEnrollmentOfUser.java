//package br.org.sesisenai.ava.security.auths;
//
//import br.org.sesisenai.ava.security.model.USerDetails;
//import lombok.AllArgsConstructor;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
//@Component
//@AllArgsConstructor
//public class IsEnrollmentOfUser implements AuthorizationManager<RequestAuthorizationContext> {
//    @Override
//    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        AuthorizationManager.super.verify(authentication, object);
//    }
//
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
//        boolean decision = false;
//        USerDetails uSerDetails = (USerDetails) supplier.get().getPrincipal();
//        Map<String, String> variables = object.getVariables();
//
//        try {
//            System.out.println(object.getRequest().getReader().lines().collect(Collectors.joining(System.lineSeparator())).contains("usuarioId : "+uSerDetails.getUsuario().getId()));
//            System.out.println(object.getRequest().getReader().lines().collect(Collectors.joining(System.getProperty("usuarioId")))
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Long inscricaoId = Long.parseLong(variables.get("cursoId"));
//
//        return new AuthorizationDecision(decision);
//    }
//}
