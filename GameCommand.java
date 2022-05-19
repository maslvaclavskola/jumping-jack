public class GameCommand{
	private final String msg;
	public GameCommand(String msg){
		this.msg=msg;
	}
	@Override
	public String toString(){
		return msg;
	}
}
