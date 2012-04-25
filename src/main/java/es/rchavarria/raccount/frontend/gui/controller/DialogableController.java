package es.rchavarria.raccount.frontend.gui.controller;


public interface DialogableController<T> extends GuiController {

	/**
	 * @return El resultado satisfactorio del dialogo donde se incruste el panel
	 */
	public T getElement();

	/**
	 * @return <code>true</code> si todos los valores del panel estan correctos
	 * 
	 *         TODO Debería lanzar una excepción en lugar de retornar true/false?
	 */
	public boolean areValuesOK();
}
