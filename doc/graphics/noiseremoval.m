% Noise removal
I = imread('../img/picasso.png');
I_gray = rgb2gray(I);

% mean
k = ones(3,3) / 9;    % Define mean filter
I_mean = imfilter(I_gray, k); % Apply to image

% median
I_median = medfilt2(I_gray, [3,3]); % Apply to image

% Rank
I_rank = ordfilt2(I_gray, 25, ones(5,5)); % Apply to image

% Gaussian
l = fspecial('gaussian', [5,5], 2); % Define Gaussian filter
I_gaussian = imfilter(I_gray, l); % Apply to image.

subplot(2,2,1), imshow(I_mean);
title('Mean filter')
subplot(2,2,2), imshow(I_median);
title('Median filter')
subplot(2,2,3), imshow(I_rank);
title('Rank filter')
subplot(2,2,4), imshow(I_gaussian);
title('Gaussian filter')