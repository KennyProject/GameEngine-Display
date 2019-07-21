package com.kenny.engine;

public class Engine 
{	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "Game Engine 0.1";
	public EngineWindow window;
	
	public void run()
	{
		this.init();
	}
	
	/**
	 * Тут инициализируются все необходимые компоненты для движка.
	 */
	public void init()
	{
		this.window = new EngineWindow(WIDTH, HEIGHT, TITLE);
		this.window.create();
		this.update();
	}
	
	
	/**
	 * В этом методе будет вся система редеринга Open Gl.
	 */
	public void update()
	{
		while(!this.window.isClosedRequest())
		{
			this.window.update();
		}
		
		this.window.destroy();
	}
}

