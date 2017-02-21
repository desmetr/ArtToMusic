ImageReader imageReader = (ImageReader) iter.next();
imageReader.setInput(is);

BufferedImage image = imageReader.read(0);
int height = image.getHeight();
int width = image.getWidth();

Raster raster = image.getRaster();
int[][] bins = new int[3][256];

for (int i = 0; i < width; i++) 
{
    for (int j = 0; j < height; j++) 
    {
        bins[0][raster.getSample(i, j, 0)]++;
        bins[1][raster.getSample(i, j, 1)]++;
        bins[2][raster.getSample(i, j, 2)]++;
    }
}