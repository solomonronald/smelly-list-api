package com.smellylist.api.roles;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents Roles for Smelly List.
 * Default user on initial creation is ROLE_USER.
 */
public enum SmellyRole implements GrantedAuthority {
    /**
     * Super User, Administrator.
     */
    ROLE_ADMIN,

    /**
     * Moderators, having higher privilege than users.
     */
    ROLE_MOD,

    /**
     * Default users.
     */
    ROLE_USER;

    /**
     * The roles can be distinguished by one character.
     */
    private final char uniqueCharacterId;

    SmellyRole() {
        // Set uniqueCharacterID as 6th character of enum role name.
        this.uniqueCharacterId = name().charAt(5);
    }

    /**
     * Convert Array of smelly roles to a concatenated string of their uniqueCharacterId.
     * @param roles Array of smelly roles
     * @return String of uniqueCharacterId.
     */
    public static String encodeRoles(SmellyRole[] roles) {
        StringBuilder roleString = new StringBuilder();
        for (SmellyRole role: roles) {
            roleString.append(role.uniqueCharacterId);
        }
        return roleString.toString();
    }

    /**
     * Convert a string of roles to an array of SmellyRole
     * @param roleString Contains String of uniqueCharacterId representing roles.
     * @return Array of smelly roles
     */
    public static SmellyRole[] decodeRoles(String roleString) {
        // Optimise decoding ?
        Set<SmellyRole> roleSet = new HashSet<>(SmellyRole.values().length);
        for (SmellyRole smellyRole: SmellyRole.values()) {
            if (roleString.toUpperCase().indexOf(smellyRole.uniqueCharacterId) != -1) {
                roleSet.add(smellyRole);
            }
        }
        return roleSet.toArray(SmellyRole[]::new);
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public static class Code {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MOD = "ROLE_MOD";
        public static final String USER = "ROLE_USER";
    }
}
