package lesson01;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * -Djava.library.path=target/natives
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class WindowGame extends BasicGame {

    /** Container. */
	private GameContainer container;

    /** Carte niveau. */
    private Map map ;

    /** Joueur. */
    private Player player = new Player(this.map) ;

    /** Camera. */
    private Camera camera = new Camera(player);

    /** Trigger controller. */
    private TriggerController triggers = new TriggerController(map, player);

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 800, 600, false).start();
	}

	private WindowGame() {
		super("Lesson 5 :: CollisionGame");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		//initialisation de la carte
        map = new Map();
        this.player.setMap(map);
        this.player.init();
        triggers.setPlayer(this.player);
        triggers.setMap(this.map);
        PlayerController controller = new PlayerController(this.player);
        controller.setPlayer(this.player);
        container.getInput().addKeyListener(controller);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
	    this.camera.positionne(container, g);
        //affichage du calque de collision
	    this.map.renderBackground();
	    //mise a jour du joueur
	    this.player.render(g);
        this.map.renderForeground();
	}

    /**
     * update the game logic
     * @param container The game container
     * @param delta The amount of time since last update in milliseconds
     * @throws SlickException
     */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
	    triggers.updateTrigger();
        this.player.update(delta);
        this.camera.update(container);
	}

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }

}
