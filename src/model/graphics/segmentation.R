library("png")
library("grid")

# load the PNG into an RGB image object
dim(image)

# show the full RGB image
#grid.raster(image)

# show the 3 channels in separate images
# copy the image three times
image.R = image
image.G = image
image.B = image

# zero out the non-contributing channels for each image copy
image.R[,,2:3] = 0
image.G[,,1]=0
image.G[,,3]=0
image.B[,,1:2]=0

# build the image grid
img1 = rasterGrob(image.R)
img2 = rasterGrob(image.G)
img3 = rasterGrob(image.B)

# reshape image into a data frame
df = data.frame(
  red = matrix(image[,,1], ncol=1),
  green = matrix(image[,,2], ncol=1),
  blue = matrix(image[,,3], ncol=1)
)

# compute the k-means clustering
K = kmeans(df, k)
df$label = K$cluster

# replace the color of each pixel in the image with the mean 
# R,G, and B values of the cluster in which the pixel resides:

# get the coloring
colors = data.frame(
  label = 1:nrow(K$centers), 
  R = K$centers[,"red"],
  G = K$centers[,"green"],
  B = K$centers[,"blue"]
)

# merge color codes on to df
# IMPORTANT: we must maintain the original order of the df after the merge!
df$order = 1:nrow(df)
df = merge(df, colors)
df = df[order(df$order),]
df$order = NULL

# get mean color channel values for each row of the df.
R = matrix(df$R, nrow=dim(image)[1])
G = matrix(df$G, nrow=dim(image)[1])
B = matrix(df$B, nrow=dim(image)[1])

# reconstitute the segmented image in the same shape as the input image
image.segmented = array(dim=dim(image))
image.segmented[,,1] = R
image.segmented[,,2] = G
image.segmented[,,3] = B

# View the result
#grid.raster(image.segmented)