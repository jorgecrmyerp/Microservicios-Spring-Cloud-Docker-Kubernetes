/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.microservicio.app.autenticacion.oauth.services.IUsuarioService;
import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

/**
 * The Class InfoAdicionalToken.
 * Añadimos informacion adicional al token,lo llaman CLAIMS
 */
@Component
public class InfoAdicionalToken implements TokenEnhancer{


    /** The i usuario service. para obtener los datos del usuario */
    @Autowired
    private IUsuarioService iUsuarioService;

    /**
     * Enhance.
     * obtenemos datos adicionales del usuario
     * @param accessToken    the access token
     * @param authentication the authentication, aqui vienen los datos del usuario
     * @return the o auth 2 access token
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
	//tenemos utilizarlo para guardar la informacion parametro-valor
	Map<String,Object> info = new HashMap<String,Object>();

	Usuario usuario =iUsuarioService.findByUsername(authentication.getName());

	info.put("nombre", usuario.getNombre());
	info.put("apellido",usuario.getApellido());
	info.put("correo", usuario.getEmail());
	info.put("activo", usuario.getEnabled()?"activo":"inactivo");
	info.put("intentos", usuario.getIntentos()!=null?usuario.getIntentos().toString():0);

	//convierto el accessToken a default.... porque es muy generico
	//y le añadimos los datos del usuario
	((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

	return accessToken;

    }

}
