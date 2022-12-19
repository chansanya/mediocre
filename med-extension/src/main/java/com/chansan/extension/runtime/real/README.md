# RealESRGAN

We have provided the following models:

1. realesr-animevideov3 (default)
2. realesrgan-x4plus
3. realesrgan-x4plus-anime

Command:

1. ./realesrgan-ncnn-vulkan.exe -i input.jpg -o output.png
2. ./realesrgan-ncnn-vulkan.exe -i input.jpg -o output.png -n realesr-animevideov3
3. ./realesrgan-ncnn-vulkan.exe -i input_folder -o outputfolder -n realesr-animevideov3 -s 2 -f jpg
4. ./realesrgan-ncnn-vulkan.exe -i input_folder -o outputfolder -n realesr-animevideov3 -s 4 -f jpg

Commands for enhancing anime vide[README_ubuntu.md](..%2Frealesrgan-ncnn-vulkan-20220424-ubuntu%2FREADME_ubuntu.md)os:

```shell
ffmpeg -i onepiece_demo.mp4 -qscale:v 1 -qmin 1 -qmax 1 -vsync 0 tmp_frames/frame%08d.jpg
    
realesrgan-ncnn-vulkan.exe -i tmp_frames -o out_frames -n realesr-animevideov3 -s 2 -f jpg -v
    
ffmpeg -i out_frames/frame%08d.jpg -i onepiece_demo.mp4 -map 0:v:0 -map 1:a:0 -c:a copy -c:v libx264 -r 23.98 -pix_fmt yuv420p output_w_audio.mp4


realesrgan-ncnn-vulkan.exe -i E:\Users\yf\Downloads\pdf\test\realesrgan-ncnn-vulkan-20220424-windows\tmp_frames\3b3940f5-aba6-4a10-8dfc-3604ade7e0d8/frame00000026.png -o  E:\Users\yf\Downloads\pdf\test\realesrgan-ncnn-vulkan-20220424-windows\out_frames\3b3940f5-aba6-4a10-8dfc-3604ade7e0d8/frame00000026.jpg -n realesr-animevideov3 -s 2 -f jpg
```


   
