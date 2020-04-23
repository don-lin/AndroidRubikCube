package dl.gl1.RubikCubeObjects;

public class CubeData {
    public int[][] data;
    public int layers;
    
    public int[][] getData(){
	return data;
    }
    
    public CubeData(int layers) {
	this.layers=layers;

	data=new int[6][layers*layers];
	for(int i=0;i<6;i++)
	    for(int j=0;j<data[i].length;j++)
			data[i][j]=i;
    }
    
    public void rotateLFaceMatrix(int[] matrix) {
	int[] temp=new int[layers*layers];
	
	for(int i=0;i<layers;i++) {
	    for(int j=0;j<layers;j++) {
		temp[j*layers+(layers-1-i)]=matrix[i*layers+j];
	    }
	}
	for(int i=0;i<layers*layers;i++) {
	    matrix[i]=temp[i];
	}
    }
    
    public void rotateRFaceMatrix(int[] matrix) {
	for(int i=0;i<3;i++)
	    rotateLFaceMatrix(matrix);
    }
    
    public void rotate(int method) {
	
    }
}
