package br.org.sesisenai.ava.security.auths;

import br.org.sesisenai.ava.entity.Inscricao;
import br.org.sesisenai.ava.security.model.USerDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class IsOnlyUserCourse implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        USerDetails uSerDetails = (USerDetails) supplier.get().getPrincipal();
        Map<String, String> variables = object.getVariables();
        boolean decision = false;

        Long courseId = Long.parseLong(variables.get("cursoId"));
        for(Inscricao inscricao : uSerDetails.getUsuario().getInscricoes()) {
            if (inscricao.getCurso().getId() == courseId) {
                decision = true;
            }
        };

        return new AuthorizationDecision(decision);
    }
}
