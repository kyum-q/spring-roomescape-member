package roomescape.dto;

import roomescape.domain.Member;
import roomescape.domain.Role;

public record MemberModel(Long id, String name, String email, Role role) {
    public static MemberModel from(Member member) {
        return new MemberModel(member.id(), member.name(), member.email(), member.role());
    }
}
