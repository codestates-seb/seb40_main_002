package main.project.server.email.event;

import lombok.Getter;
import main.project.server.member.entity.Member;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;
    public MemberRegistrationApplicationEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
}
