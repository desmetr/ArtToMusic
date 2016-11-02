% Read and display image.
I = imread('img/picasso.tif');
figure, imshow(I), title('original');

% Apply Canny.
I = rgb2gray(I);
BWCanny = edge(I,'canny');
figure, imshow(BWCanny), title('Canny');

BWCanny