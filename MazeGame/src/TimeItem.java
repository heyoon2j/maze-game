public class TimeItem extends Item {

    enum Item {
        PLUS_TIME(0, 1), MINUS_TIME(1, -1);

        int number;
        int addTime;

        private Item(int number, int addTime) {
            this.number = number;
            this.addTime = addTime;
        }

        public int getAddTime() {
            return addTime;
        }
    }

    private TimeItem.Item item;

    TimeItem (TimeItem.Item item) {
        this.item = item;
    }

    @Override
    public void use(Player player) {
        long time = Long.parseLong(player.getTimeRecord());
        time = time + this.item.getAddTime() * 1000;

        System.out.println("아이템이 사용되었습니다. 추가 시간 " +this.item.getAddTime()+ "초 적용");
//        System.out.println("Time : "+ time);
        player.setTimeRecord(String.valueOf(time));
    }
}
