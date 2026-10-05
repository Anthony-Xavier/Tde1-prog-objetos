
import sistema.Empresa;
import ui.MenuPrincipal;

public class Main {

	public static void main(String[] args) {
		MenuPrincipal menu = new MenuPrincipal(new Empresa());
		menu.mostrar();
	}

}
