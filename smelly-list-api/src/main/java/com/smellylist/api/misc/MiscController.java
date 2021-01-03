package com.smellylist.api.misc;

import com.smellylist.api.common.models.response.SimpleResponse;
import com.smellylist.api.roles.SmellyRole;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscController {

    /**
     * A test endpoint which returns polo.
     * @return "Polo"
     */
    @Operation(summary = "Test Marco", description = "Test endpoint that returns \"Polo\"")
    @GetMapping("/marco")
    public ResponseEntity<SimpleResponse> marcoPolo() {
        return new ResponseEntity<>(new SimpleResponse("Polo"), HttpStatus.OK);
    }

    /**
     * Authenticated test endpoint which returns psst.
     * @return "Psst"
     */
    @Operation(summary = "Psst", description = "Test endpoint that replies \"Psst\"")
    @GetMapping("/psst")
    public ResponseEntity<SimpleResponse> psst() {
        return new ResponseEntity<>(new SimpleResponse("Psst"), HttpStatus.OK);
    }


    @GetMapping("/role-check/admin")
    @Secured(SmellyRole.Code.ADMIN)
    public ResponseEntity<SimpleResponse> roleCheckAdmin() {
        return new ResponseEntity<>(new SimpleResponse("Yes it is admin"), HttpStatus.OK);
    }

    @GetMapping("/role-check/mod")
    @Secured(SmellyRole.Code.MOD)
    public ResponseEntity<SimpleResponse> roleCheckMod() {
        return new ResponseEntity<>(new SimpleResponse("Yes it is moderator"), HttpStatus.OK);
    }

    @GetMapping("/role-check/ad-mod")
    @Secured({SmellyRole.Code.MOD, SmellyRole.Code.ADMIN})
    public ResponseEntity<SimpleResponse> roleCheckAdminMod() {
        return new ResponseEntity<>(new SimpleResponse("Yes it is admin/mod"), HttpStatus.OK);
    }
}
