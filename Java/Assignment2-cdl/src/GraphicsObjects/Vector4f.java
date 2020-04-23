package GraphicsObjects;



public class Vector4f {

	public float x=0;
	public float y=0;
	public float z=0;
	public float a=0;

	// default constructor
	// initialize four variables as 0
	public Vector4f() 
	{  
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		a = 0.0f;
	}
	
	//initializing constructor
	/*
	 * @param x the value of vector's x-axis
	 * @param y the value of vector's y-axis
	 * @param z the value of vector's z-axis
	 * @param a the value of vector's a-axis
	 */
	public Vector4f(float x, float y, float z,float a) 
	{ 
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}
	
	// @param the vector that this vector will add
	// @return the vector of the vector add the specific vector
	public Vector4f PlusVector(Vector4f Additonal) 
	{ 
		 return new Vector4f(x+Additonal.x,y+Additonal.y,z+Additonal.z,a+Additonal.a);
	} 

	// @param the vector that this vector will minus
	// @return the vector of the vector minus the specific vector
	public Vector4f MinusVector(Vector4f Minus) 
	{ 
		return new Vector4f(x-Minus.x,y-Minus.y,z-Minus.z,a-Minus.a);
	}
	    
	// @param the point that this vector will add
	// @return the position of the vector add the specific point
	public Point4f PlusPoint(Point4f Additonal) 
	{ 
		 return new Point4f(x+Additonal.x,y+Additonal.y,z+Additonal.z,a+Additonal.a);
	} 
	//Do not implement Vector minus a Point as it is undefined 
	
	//@param the ratio that the vector should scale
	//@return the vector that this vector scale
	public Vector4f byScalar(float scale )
	{
		 return new Vector4f(scale*x,scale*y,scale*z,scale*a);
	}
	
	//@return the result of the negative vector
	public Vector4f  NegateVector()
	{
	 return new Vector4f(-x,-y,-z,-a);
	}
	
	//@return the length of the vector 
	public float length()
	{
	    return (float) Math.sqrt(x*x+y*y+z*z+a*a);
	}
	
	//@return the vector which has a same direction with this vector but length is 1
	public Vector4f Normal()
	{
	    return byScalar(1/length());
	} 
	
	//@param the vector that this vector will dot
	//@return the result of the dot calculation
	public float dot(Vector4f v)
	{ 
	    return ( this.x*v.x + this.y*v.y + this.z*v.z+ this.a*v.a);
	}

	//@param the vector that this vector will cross
	//@return the result of the cross calculation
	public Vector4f cross(Vector4f v)  
	{ 
	    float u0 = (this.y*v.z - z*v.y);
            float u1 = (z*v.x - x*v.z);
            float u2 = (x*v.y - y*v.x);
            float u3 = 0; //ignoring this for now  
            return new Vector4f(u0,u1,u2,u3);
	}
 
}
	 
	   

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                4 D                                      MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */