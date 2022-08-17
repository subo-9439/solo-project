package api.vi.user.entity;


import lombok.Getter;

public enum Sex {

    MAN("남자"),WOMAN("여자"),TTT("제3의성");

    @Getter
    String value;
    Sex(String value){
        this.value = value;
    }
}
