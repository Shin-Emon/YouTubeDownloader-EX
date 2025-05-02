from yt_dlp import YoutubeDL
import sys
from yt_dlp import YoutubeDL
from os.path import expanduser
import socket

arguments = sys.argv

mode = arguments[1]

print("============================================")
print("YouTube Downloader EX CORE v1.31")
print("LOAD COMPLETE! Python v3.11")
print("Author: Shintaro")
print("============================================")

# 関数定義
def prepare(target_port):
    global tcp_client
    tcp_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    target_ip = 'localhost'
    buffer_size = 4096
    tcp_client.connect((target_ip, target_port))

    response = tcp_client.recv(buffer_size)
    print(response)
    
def send(str):
    tcp_client.send(str)

def close():
    send(b'ALLDONE')
    tcp_client.close()

def receiver(d):
    if d['status'] == 'downloading':
        send(bytes(d['_percent_str'].replace('%', '') + '\r\n', encoding='utf-8', errors='replace'))
    elif d['status'] == 'finished':
        send(b'finished\r\n')



def download(args):
    prepare(int(args[9]))

    url = args[2]
    dest_dir = args[3]
    custom_filename_op = args[4]
    output_type = args[5]
    output_ext = args[6]
    quality_value = int(args[7])
    add_metadata = args[8]

    options = {
        'ffmpeg_location': expanduser("~") + "\\YouTubeDownloaderEX",
        'noprogress': True,
        'progress_hooks': [receiver],
        'postprocessors': []
    }

    if output_type == 'audioonly':
        options['audio-format'] = output_ext
        options['audio-quality'] = quality_value
        options['postprocessors'].append({
            'key': 'FFmpegExtractAudio',
            'preferredcodec': output_ext,
            'preferredquality': f'{quality_value}'
        })

    elif output_type == 'video':
        options['format'] = f'bestvideo[height<={quality_value}][ext={output_ext}]+bestaudio/best'
        options['merge_output_format'] = output_ext

    else:
        print('ERROR!!!!!!!!!!!!!!!!!')
        exit(-1)


    if add_metadata == 'true':
        options['postprocessors'].append({
            'key': 'FFmpegMetadata',
            'add_metadata': True
        })
    
    options['paths'] = {
        'home': dest_dir
    }
    options['verbose'] = False

    options['lang'] = 'ja'


    if custom_filename_op != ':false':
        options['outtmpl'] = f'{custom_filename_op}.%(ext)s'

    with YoutubeDL(options) as ydl:
        ydl.download([url])
        close()

def fetch_info(args):
    prepare(int(args[3]))

    options = {
    }
    url = args[2]
    
    with YoutubeDL(options) as ydl:
        info = ydl.extract_info(url, download=False)
        title = info['title']
        duration = info['duration']
        send(bytes('title=' + title + '\r\n', encoding='utf-8', errors='replace'))
        send(bytes('duration=' + str(duration) + '\r\n', encoding='utf-8', errors='replace'))
        close()



if mode == 'dl':
    download(arguments)
elif mode == 'info':
    fetch_info(arguments)
else:
    print("=======================================")
    print("ERROR: No such 'mode' option: " + mode)
    print("This is probably because of the program's bug.")
    print("Please inform this problem to the developer.")
    print("=======================================")

