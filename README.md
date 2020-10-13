# maze-game
A simple maze Game written in Java

* 미로를 랜덤하게 생성하며, 해당 미로를 탈출하는 게임

## 1. 프로젝트 주제
* 임의 생성 미로 게임

## 2. 프로젝트 기간
* 2020.10.14 ~ 2020.10.21

## 3. 프로젝트 내용
1. Set up
    * JDK Version : 14
    * OS : Mac, Windows 7
2. 구현 내용
    1. Maze
        * 총 1개로 사용.
        * Singleton Pattern 사용(Lazy Instantiation)
        * 게임이 시작하면 랜덤하게 생성
        * 난이도에 따라 미로 변경(맵 크기 늘리기)
            * 맵 한 칸당 Array[3][3]로 설정    
            * Beginner : 5*5
            * Intermediate : 10*10
            * Expert : 15*15
        * Timer 동작
    2. Item
        * Factory Method Pattern 사용
        * 위치는 램덤하게 아이템 생성
        * 시간을 늘리는/줄이는 아이템
        * 속도가 빨라지는/느려지는거 아이템
        * 시야가 일정시간 동안 사라지는 아이템
        * 벽을 부수는 아이템
    3. User
        * Maze - User, Observer Pattern 사용
        * 방향키로 움직임 설정
        * 속도 결정
    4. Rank System
        * 0점 이면 랭킹에 등록되지 않는다.
        * 1등부터 10등까지 등록
        * 기본 랭크 파일 만들기(파일 입출력)
    5. Enemy(추가 사항)
        * 팩맨 처럼 나를 쫒아 올건지?(가까이 왔을 때 쫒아오도록)
    6. 2인 플레이(추가 사항)
    7. 추가 아이디어(추가 사항)
        * 난이도 Expert에서 왔던 길은 돌아가지 못하게 하기
         
## 4. 유저 시나리오
* Maze는 게임 시작 시, 자동으로 랜덤하게 생성해서 출력한다.
* 목표는 빨리 탈출하는 것, (랭킹 시스템: DB 사용 X, 파일 입출력으로 진행)
    1) Game Start
    2) Level 설정(Beginner, Intermediate, Advanced)
    3) User 인원 선택(1 or 2) // 나중에 멀티 게임을 위한 것
    4) Game Play & Timer Start
    5) 일정 시간 안에 탈출 & 시간이 오버되면 0점으로 종료
    6) 랭킹 등록


## 5. 소프트웨어 구조 설계
* UML




