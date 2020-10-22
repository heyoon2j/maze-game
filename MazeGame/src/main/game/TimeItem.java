package main.game;

public class TimeItem extends Item {
    int addedTime;

    public TimeItem(int addedTime) {
        this.addedTime = addedTime;
    }

    @Override
    public void use(Player player) {
        long time = player.getTimeRecord() + this.addedTime;
        System.out.println("아이템이 사용 되었습니다. 추가 시간 " + (this.addedTime/1000) + "초 적용");
        player.setTimeRecord(time);
    }
}
