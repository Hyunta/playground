package com.example.springjpaground.nplusone.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Team(String name, List<Member> members, List<Item> items) {
        this.name = name;
        if (members != null) {
            this.members = members;
        }
        if (items != null) {
            this.items = items;
        }
    }

    public void addMember(Member member) {
        members.add(member);
        member.updateTeam(this);
    }

    public void addItem(Item item) {
        items.add(item);
        item.updateTeam(this);
    }
}
