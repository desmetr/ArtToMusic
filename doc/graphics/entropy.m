function H = MyEntropy()
% computes the entropy of the input intensity image x
% Assume input image is of uint8

% display the image
x = imread('../../img/white.jpg');
%figure, imshow(x)

% 248 249 250 251 252 253 254 255 256]

% get height and width of image
[Height, Width] = size(x);

% get the histogram of the image in 256 bins
[m, Binsx] = imhist(rgb2gray(x));
%[m, Binsx] = imhist(x);
% Normalize the counts
m = m / (Height * Width);

% output the sum of the histogram values
sprintf('the sum of the histogram values is = %g', sum(m));

% Note check that the sum of the probabilities is one.
%figure, plot(Binsx, m, 'k')
%xlabel('Pixel value'), ylabel('relative count')

% in case of error, log of zero => add a very small value to m)
H = sum(-m.*log2(m + 1e-10));
sprintf('the entropy of tblackhe image is = %g', H);