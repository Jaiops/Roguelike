package game;

/**
 * Created by Johan on 2016-07-14.
 */
public class MonsterFactory {

    static Monster createGoblin(Position p){

        Monster m = new Monster(p,new StandardEvilAi(),"Goblin",20);
        m.setAlignment(Alignment.EVIL);
        m.setDamage(3);
        m.setSprite(3,2);
        m.setSpeed(1.0);

        return m;
    }
    static Monster createOrc(Position p){

        Monster m = new Monster(p,new StandardEvilAi(),"Orc",30);
        m.setAlignment(Alignment.EVIL);
        m.setDamage(5);
        m.setSprite(47,2);
        m.setSpeed(2.0);
        return m;
    }
    static Monster createSpeed(Position p){

        Monster m = new Monster(p,new StandardEvilAi(),"Speed Spider",30);
        m.setAlignment(Alignment.EVIL);
        m.setDamage(5);
        m.setSprite(53,4);
        m.setSpeed(0.5);
        return m;
    }
    static Monster createDevil(Position p){

        Monster m = new Monster(p,new StandardEvilAi(),"Diablo",150);
        m.setAlignment(Alignment.EVIL);
        m.setDamage(35);
        m.setSprite(24,5);
        m.setSpeed(0.8);
        return m;
    }
}
