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
    ROLE_ADMIN(4),

    /**
     * Moderators, having higher privilege than users.
     */
    ROLE_MOD(2),

    /**
     * Default users.
     */
    ROLE_USER(1);

    private final int maskValue;

    SmellyRole(int maskValue) {

        this.maskValue = maskValue;
    }

    /**
     * Convert Array of smelly roles to an int mask.
     * @param roles Array of smelly roles
     * @return Integer mask of roles.
     */
    public static int encodeRoles(SmellyRole[] roles) {
        int sum = 0;
        for (SmellyRole role: roles) {
            // Using or in order to make sure only unique roles are "added".
            sum |= role.maskValue;
        }
        return sum;
    }

    /**
     * Convert a role mask to an array of SmellyRole
     * @param mask int representing mask of multiple roles.
     * @return Array of smelly roles
     */
    public static SmellyRole[] decodeRoles(int mask) {
        // Holds the sum of all SmellyRole maskValues.
        // ex. [4 + 2 + 1] = 7
        int maxAvailableRole = 0;

        // Holds the possible number of valid roles suspected to be present in input mask value
        int possibleNumberOfValidRolesInMask = 0;

        for (var role: values()) {
            maxAvailableRole += role.maskValue;
            // Check if role is present in mask.
            // If role.maskValue ex. 2
            // is present in mask ex. 3
            // 3 & 2 = 2 will be greater than 0
            possibleNumberOfValidRolesInMask += (( mask & role.maskValue ) > 0 ) ? 1 : 0;
        }

        // Check if roles in mask is valid.
        int validRolesInMask = ((mask <= maxAvailableRole) && (mask >= 0)) ? mask : 0;

        // If roles in mask are valid
        if (validRolesInMask > 0) {
            // Create an array of possible number of valid roles in mask.
            // possibleNumberOfValidRolesInMask is now safe to use as validRolesInMask is greater than 0
            SmellyRole[] resultRoleArray = new SmellyRole[possibleNumberOfValidRolesInMask];
            // Current index to store in resultRoleArray
            int index = 0;
            for(var role: values()) {
                // If role.maskValue is present in validRolesMask then add that role to result array
                if ((role.maskValue & validRolesInMask) > 0) {
                    resultRoleArray[index] = role;
                    index++;
                }
            }
            return resultRoleArray;
        } else {
            // If there is no valid role in mask the return empty array
            return new SmellyRole[]{};
        }
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
