package site.nansan.user.dto;

import lombok.Getter;
import lombok.Setter;
import site.nansan.user.domain.Role;

@Getter
@Setter
public class RoleUpdateRequest {
    private Role newRole;
}