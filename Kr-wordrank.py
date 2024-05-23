from krwordrank.word import KRWordRank
from konlpy.tag import Okt
import re

text = "오늘은 정말 최악의 날이었다. 아침부터 지각했다. 그놈의 알람이 또 울리지 않았다. 급하게 집을 나서면서부터 분노가 치밀어 올랐다. 지각 때문에 시험 시작 시간에 맞추지 못할 것 같은 불안감이 나를 더욱 짜증나게 했다. 학교에 도착했을 때는 이미 시험이 시작된 지 10분이나 지나 있었다. 문을 두드리며 들어가는데, 모든 시선이 나를 향하는 그 순간의 분노를 어떻게 표현할 수 있을까. 시험 감독관은 나를 보고 한숨을 쉬었다. 또 지각이군요. 그 한마디가 나를 더욱 화나게 했다. 이미 늦은 것을 알고 있는데, 그 말이 뭐가 필요한지. 분노를 삼키며 자리에 앉아 시험을 시작했지만, 머릿속이 하얗게 변한 채 문제들이 눈에 들어오지 않았다. 그저 지각한 것에 대한 분노와 시험 문제의 난해함이 나를 더 미치게 만들었다. 시험이 끝난 후에도 마음이 진정되지 않았다. 친구들과 얘기하려고 했지만, 지각했다는 사실에 대해 농담을 하는 그들의 모습에 분노가 치밀었다. 너 또 지각했어? 이번 시험 망쳤겠네. 그 말을 듣고 나서야 화가 폭발할 것 같았다. 시험 성적이 나오면 어차피 다 알게 될 텐데, 굳이 상기시켜야 했는지. 지각 하나로 이렇게 망가질 줄이야. 집에 돌아오는 길, 버스 안에서 오늘 하루의 지각과 시험을 생각하며 분노를 억누를 수 없었다. 또 한 번의 지각으로 망쳐버린 중요한 시험, 그리고 그로 인해 쌓인 분노가 나를 잠식하고 있었다. 하루 종일 느낀 이 분노가 쉽게 가라앉지 않을 것 같다. 내일은 이 분노를 어떻게든 해소할 방법을 찾아야겠다."

def split_noun_sentences(text):
    okt = Okt()
    sentences = text.replace(". ",".")
    sentences = re.sub(r'([^\n\s\.\?!]+[^\n\.\?!]*[\.\?!])', r'\1\n', sentences).strip().split("\n")
    
    result = []
    for sentence in sentences:
        if len(sentence) == 0:
            continue
        sentence_pos = okt.pos(sentence, stem=True)
        nouns = [word for word, pos in sentence_pos if pos == 'Noun']
        if len(nouns) == 1:
            continue
        result.append(' '.join(nouns) + '')
        
    return result

min_count = 3   # 단어의 최소 출현 빈도수 (그래프 생성 시)
max_length = 10 # 단어의 최대 길이
wordrank_extractor = KRWordRank(min_count=min_count, max_length=max_length)
beta = 0.85    # PageRank의 decaying factor beta
max_iter = 20
texts = split_noun_sentences(text)
keywords, rank, graph = wordrank_extractor.extract(texts, beta, max_iter)
for word, r in sorted(keywords.items(), key=lambda x:x[1], reverse=True):
        print(word)