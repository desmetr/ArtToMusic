folder = pwd;
baseFileName = 'triangle.jpg';

% Get the full filename, with path prepended.
fullFileName = fullfile(folder, baseFileName);

if ~exist(fullFileName, 'file')
	% Didn't find it there.  Check the search path for it.
	fullFileName = baseFileName; % No path this time.
	if ~exist(fullFileName, 'file')
		% Still didn't find it.  Alert user.
		errorMessage = sprintf('Error: %s does not exist.', fullFileName);
		uiwait(warndlg(errorMessage));
		return;
	end
end

rgbImage = imread(fullFileName);

% Get the dimensions of the image.  numberOfColorBands should be = 3.
[rows, columns, numberOfColorChannels] = size(rgbImage);

% Display the original color image.
subplot(2, 2, 1);
imshow(rgbImage);
title('Original Color Image', 'FontSize', fontSize, 'Interpreter', 'None');

% Enlarge figure to full screen.
set(gcf, 'Units', 'Normalized', 'Outerposition', [0, 0, 1, 1]);

% Extract the individual red, green, and blue color channels.
redChannel = rgbImage(:, :, 1);
greenChannel = rgbImage(:, :, 2);
blueChannel = rgbImage(:, :, 3);

% Get the color of the background from the upper left pixel
backgroundRGB = impixel(rgbImage, 1,1)

% Get a binary image so that we just need to worry about shapes, not colors.
binaryImage = ~(redChannel == backgroundRGB(1) & greenChannel == backgroundRGB(2) & blueChannel == backgroundRGB(3));

% Display the binary image.
subplot(2, 2, 2);
imshow(binaryImage);
title('Binary Image', 'FontSize', fontSize, 'Interpreter', 'None');