import com.mygdx.Character.Player;

import java.util.List;
import java.util.Map;

public class Game {
   private List<Map> maps;

   private Player player;
   private int difficulty;
   private boolean isWin = false;

   public Game(List<Map> maps,Player player,int difficulty){
      this.maps = maps;
      this.player = player;
      this.difficulty = difficulty;

   }
   public List<Map> getMaps() {
      return maps;
   }
   public Player getPlayer() {
      return player;
   }
   public int getDifficulty() {
      return difficulty;
   }

   public void play(){
   };
}
