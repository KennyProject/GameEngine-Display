package com.kenny.engine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class EngineWindow 
{
	private int width;
	private int height;
	public IntBuffer bufferedWidth;
	public IntBuffer bufferedHeight;
	private String title;
	public long id;
	public GLFWVidMode videoMode;
	
	public EngineWindow(int width, int height, String title) 
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	/**
	 * «десь происходит создание диспле€. ѕервое провер€етс€ установлена ли GLFW, далее создаЄтс€
	 * само окно glfwCreateWindow(), и так же если окно будет 0 то приложение закроетс€.
	 * 
	 * ¬ методе try идЄт получение стека потоков и проталкиваетьс€ новый кадр. » после этого
	 * идЄт создание видео режима который берЄт в параметер ваш монитор glfwGetPrimaryMonitor().
	 * ѕосле установки параметров на окно создаЄтс€ контекст дл€ GLFW на наш айдишник окна, далее
	 * также создаЄтс€ контекст Open Gl. ¬ последнюю очередь устанавливатьс€ просматриваема€ зона
	 * дл€ Open Gl glViewport().
	 */
	public void create() 
	{
		if(!GLFW.glfwInit())
		{
			System.out.println("GLFW не инициализирован!");
			System.exit(-1);
		}
		
		this.id = GLFW.glfwCreateWindow(this.width, this.height, this.title, 
				0, 0);
		
		if(this.id == 0) 
		{
			System.out.println("ќкно равно 0, и не может быть создано!");
			System.exit(-1);
		}
		
		try (MemoryStack mem = MemoryStack.stackPush())
		{
			this.bufferedWidth = BufferUtils.createIntBuffer(1);
			this.bufferedHeight = BufferUtils.createIntBuffer(1);
			GLFW.glfwGetWindowSize(this.id, this.bufferedWidth, this.bufferedHeight);
			
			this.videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			
			GLFW.glfwSetWindowTitle(this.id, this.title);
			GLFW.glfwSetWindowSize(this.id, this.width, this.height);
			GLFW.glfwSetWindowSizeLimits(this.id, this.width, this.height, 1600, 900);
			GLFW.glfwSetWindowAspectRatio(this.id, this.width, this.height); // width / height
			GLFW.glfwSetWindowPos(this.id, 
					((this.videoMode.width() - this.bufferedWidth.get(0)) / 2), 
					((this.videoMode.height() - this.bufferedHeight.get(0)) / 2));
			
		} catch (Exception e) 
		{
		}
		
		GLFW.glfwMakeContextCurrent(this.id);
		GL.createCapabilities();
		GL11.glViewport(0, 0, this.bufferedWidth.get(), this.bufferedHeight.get());
	}
	
	/**
	 * Ётот метод просто обновл€ет наше окно.
	 */
	public void update() 
	{
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.id);
	}
	
	/**
	 * ”ничтожает контекст GLFW, и само окно.
	 */
	public void destroy() 
	{
		GLFW.glfwDestroyWindow(this.id);
	}
	
	/**
	 * Ёто просто проверка, нажал ли пользователь на крестик на окне, если да
	 * то возвращает true, иначе false.
	 */
	public boolean isClosedRequest()
	{
		return GLFW.glfwWindowShouldClose(this.id);
	}

	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public int getHeight() 
	{
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}
}
