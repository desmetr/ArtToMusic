% Read and display image.
I = imread('img/picasso.tif');
figure, imshow(I), title('original image');

% Detect objects.
I = rgb2gray(I);
BWs = edge(I, 'sobel');
figure, imshow(BWs), title('picasso edges')

% Dilate the image.
% se90 = strel('line', 3, 90);
% se0 = strel('line', 3, 0);
% BWsdil = imdilate(BWs, [se90 se0]);
% figure, imshow(BWsdil), title('dilated gradient mask');

% Fill interior gaps.
% BWdfill = imfill(BWsdil, 'holes');
% figure, imshow(BWdfill), title('binary image with filled holes');

% Remove connected objects on border.
% BWnobord = imclearborder(BWdfill, 4);
% figure, imshow(BWnobord), title('cleared border image');

% Smoothen the object.
% seD = strel('diamond',1);
% BWfinal = imerode(BWnobord,seD);
% BWfinal = imerode(BWfinal,seD);
% figure, imshow(BWfinal), title('segmented image');

% Outline object in original image.
% BWoutline = bwperim(BWfinal);
% Segout = I;
% Segout(BWoutline) = 255;
% figure, imshow(Segout), title('outlined original image');