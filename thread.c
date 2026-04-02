#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

// Shared variable to count completed threads
static int completed_threads = 0;
// Mutex for synchronization
static pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

// Thread 1: Calculate factorial
typedef struct {
    int n;
} FactorialArgs;

void* factorial_task(void* arg) {
    FactorialArgs* args = (FactorialArgs*)arg;
    int n = args->n;

    long result = 1;
    for (int i = 1; i <= n; i++) {
        result *= i;
        usleep(100000); // 100ms
    }
    printf("Faktorial dari %d = %ld\n", n, result);

    // Synchronize: update shared counter
    pthread_mutex_lock(&lock);
    completed_threads++;
    printf("Thread faktorial selesai. Total selesai: %d\n", completed_threads);
    pthread_mutex_unlock(&lock);

    return NULL;
}

// Thread 2: Display fibonacci sequence
typedef struct {
    int n;
} FibonacciArgs;

void* fibonacci_task(void* arg) {
    FibonacciArgs* args = (FibonacciArgs*)arg;
    int n = args->n;

    int a = 0, b = 1;
    printf("Deret Fibonacci hingga %d: ", n);
    for (int i = 0; i < n; i++) {
        printf("%d ", a);
        int next = a + b;
        a = b;
        b = next;
    }
    printf("\n");

    pthread_mutex_lock(&lock);
    completed_threads++;
    printf("Thread fibonacci selesai. Total selesai: %d\n", completed_threads);
    pthread_mutex_unlock(&lock);

    return NULL;
}

// Thread 3: Read text file
typedef struct {
    const char* filename;
} FileReaderArgs;

void* file_reader_task(void* arg) {
    FileReaderArgs* args = (FileReaderArgs*)arg;
    const char* filename = args->filename;

    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        fprintf(stderr, "Error membaca file: cannot open '%s'\n", filename);

        pthread_mutex_lock(&lock);
        completed_threads++;
        printf("Thread file reader selesai. Total selesai: %d\n", completed_threads);
        pthread_mutex_unlock(&lock);

        return NULL;
    }

    printf("Isi file %s:\n", filename);
    char line[1024];
    int index = 1;
    while (fgets(line, sizeof(line), file)) {
        // Remove trailing newline if present
        int len = 0;
        while (line[len] != '\0') len++;
        if (len > 0 && line[len - 1] == '\n') line[len - 1] = '\0';

        printf("%d. %s\n", index++, line);
        usleep(100000); // 100ms
    }
    fclose(file);

    pthread_mutex_lock(&lock);
    completed_threads++;
    printf("Thread file reader selesai. Total selesai: %d\n", completed_threads);
    pthread_mutex_unlock(&lock);

    return NULL;
}

int main() {
    pthread_t t1, t2, t3;

    // Prepare arguments for each thread
    FactorialArgs  factorial_args  = { .n = 5  };
    FibonacciArgs  fibonacci_args  = { .n = 10 };
    FileReaderArgs file_reader_args = { .filename = "data.txt" };

    // Create and start all three threads
    pthread_create(&t1, NULL, factorial_task,   &factorial_args);
    pthread_create(&t2, NULL, fibonacci_task,   &fibonacci_args);
    pthread_create(&t3, NULL, file_reader_task, &file_reader_args);

    // Wait for all threads to finish
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);

    printf("Semua thread selesai. Total threads selesai: %d\n", completed_threads);

    pthread_mutex_destroy(&lock);
    return 0;
}